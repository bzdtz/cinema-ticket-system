<template>
    <div class="ai-dock">
        <div v-if="open" class="ai-panel">
            <div class="ai-head">
                <span class="ai-title">选座助手</span>
                <span v-if="lastEngine" class="ai-engine" :class="lastEngine === 'rule' ? 'rule' : 'model'">{{ lastEngine === 'rule' ? '规则兜底' : lastEngine }}</span>
                <button class="ai-close" @click="open = false">×</button>
            </div>

            <div v-if="!loggedIn" class="ai-tip">
                查场次和座位要登录。
                <button class="ai-tip-btn" @click="goLogin">去登录</button>
            </div>

            <div class="ai-body" ref="body">
                <p v-if="!messages.length" class="ai-hint">
                    试试：「哪家影院有排片」「帮我挑两个连座」「这场什么时候开始」
                </p>
                <div v-for="(m, i) in messages" :key="i" class="ai-msg" :class="m.role">
                    <span class="ai-text">{{ m.text }}</span>
                    <p v-if="m.steps && m.steps.length" class="ai-steps">查了：{{ m.steps.join(' → ') }}</p>
                    <div v-if="m.draft" class="ai-draft">
                        <p class="ai-draft-line">{{ m.draft.movie }} · {{ m.draft.date }} {{ m.draft.time }}</p>
                        <p class="ai-draft-line">{{ m.draft.cinema }} {{ m.draft.hall }}</p>
                        <p class="ai-draft-line">{{ seatLabels(m.draft) }} · 合计 {{ m.draft.total }} 元</p>
                        <button class="ai-draft-btn" @click="useDraft(m.draft)">就按这个下单</button>
                        <p class="ai-draft-note">{{ m.draft.confirmToken
                            ? '我只负责选好座位，最后一步由你在页面上点确认，这枚凭证十分钟内有效。'
                            : '我只负责选好，最后一步你自己点。' }}</p>
                    </div>
                </div>
                <div v-if="busy" class="ai-msg assistant"><span class="ai-text">正在查…</span></div>
            </div>

            <div class="ai-foot">
                <input v-model="input"
                       class="ai-input"
                       type="text"
                       :disabled="busy"
                       placeholder="想查什么？"
                       @keyup.enter="send"/>
                <button class="ai-send" :disabled="busy || !input.trim()" @click="send">发送</button>
            </div>
        </div>

        <button class="ai-ball" :class="{open: open}" @click="open = !open">{{ open ? '×' : 'AI' }}</button>
    </div>
</template>

<script>
export default {
    name: "AiAssistant",
    data() {
        return {
            open: false,
            busy: false,
            input: '',
            messages: [],
            lastEngine: ''
        };
    },
    computed: {
        loggedIn() {
            return !!localStorage.getItem('token');
        },
        context() {
            const query = this.$route.query || {};
            const name = this.$route.name || '';
            const ctx = {};
            const showtimeId = query.showtimes || query.showtimeId;
            if (showtimeId) {
                ctx.showtimeId = showtimeId;
            }
            if (query.cinemaId) {
                ctx.cinemaId = query.cinemaId;
            } else if (name === 'cinemashow' && query.id) {
                ctx.cinemaId = query.id;
            }
            if (query.movieId) {
                ctx.movieId = query.movieId;
            } else if (['movie', 'introduced', 'actor', 'pic'].indexOf(name) >= 0 && query.id) {
                // /movie 会 redirect 到 /introduced，所以 $route.name 永远不是 'movie'，
                // 影片页真正落地的是 introduced/actor/pic 这三个子路由，id 都挂在 query 上
                ctx.movieId = query.id;
            }
            return ctx;
        }
    },
    methods: {
        send() {
            const text = this.input.trim();
            if (!text || this.busy) {
                return;
            }
            this.messages.push({role: 'user', text: text});
            this.input = '';
            // 没登录就别发出去：501 会被全局响应拦截器吃掉，直接 router.push('/login') 并清空 localStorage
            if (!this.loggedIn) {
                this.messages.push({
                    role: 'assistant',
                    text: '这一步要登录才能查场次和座位。点上面的「去登录」，回来接着问就行。'
                });
                this.scrollDown();
                return;
            }
            this.busy = true;
            this.scrollDown();

            const history = this.messages.slice(-8, -1).map(m => ({
                role: m.role === 'user' ? 'user' : 'assistant',
                content: m.text
            }));

            this.$axios({
                method: 'post',
                url: '/app/ai/chat',
                data: {message: text, history: history, context: this.context}
            }).then(res => {
                const data = res.data && res.data.data ? res.data.data : {};
                this.lastEngine = data.engine || 'rule';
                this.messages.push({
                    role: 'assistant',
                    text: data.answer || '没返回内容。',
                    draft: data.draft || null,
                    steps: data.steps || []
                });
            }).catch(err => {
                this.messages.push({
                    role: 'assistant',
                    text: '问不动：' + (err && err.message ? err.message : '后端没应答') + '。'
                });
            }).finally(() => {
                this.busy = false;
                this.scrollDown();
            });
        },
        useDraft(draft) {
            localStorage.setItem('aiDraft', JSON.stringify({
                showtimeId: draft.showtimeId,
                seats: draft.seats || [],
                // 确认凭证只在浏览器这一侧流转，模型那边拿不到它
                confirmToken: draft.confirmToken || '',
                confirmExpiresInSeconds: draft.confirmExpiresInSeconds || 600
            }));
            const target = {path: '/xseats', query: {showtimes: draft.showtimeId}};
            // 人就在这一场的选座页上问的：路由没变，组件不会重新 mounted，
            // 只 push 的话草稿就躺在 localStorage 里没人应用，得自己喊一声。
            const samePage = this.$route.path === target.path
                && String(this.$route.query.showtimes) === String(target.query.showtimes);
            this.$router.push(target).catch(() => {
                // 重复导航本来就该忽略，草稿已经在 localStorage 里了
            });
            if (samePage) {
                window.dispatchEvent(new CustomEvent('ai-draft-apply'));
            }
        },
        seatLabels(draft) {
            return (draft.seats || []).map(seat => seat.label).join('、');
        },
        goLogin() {
            this.$router.push({path: '/login'});
        },
        scrollDown() {
            this.$nextTick(() => {
                const body = this.$refs.body;
                if (body) {
                    body.scrollTop = body.scrollHeight;
                }
            });
        }
    }
};
</script>

<style scoped>
.ai-dock {
    position: fixed;
    right: 26px;
    bottom: 26px;
    z-index: 3000;
    font-size: 14px;
}

.ai-ball {
    width: 52px;
    height: 52px;
    border-radius: 50%;
    border: none;
    background: #eb002a;
    color: #fff;
    font-size: 16px;
    cursor: pointer;
    box-shadow: 0 4px 14px rgba(0, 0, 0, .25);
}

.ai-ball.open {
    background: #666;
}

.ai-panel {
    width: 330px;
    height: 460px;
    margin-bottom: 12px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 6px 26px rgba(0, 0, 0, .28);
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.ai-head {
    display: flex;
    align-items: center;
    padding: 10px 12px;
    border-bottom: 1px solid #eee;
}

.ai-title {
    font-weight: 600;
}

.ai-engine {
    margin-left: 8px;
    padding: 1px 6px;
    border-radius: 8px;
    font-size: 12px;
    background: #f0f0f0;
    color: #888;
}

/* engine 是模型名时（如 deepseek-v4-flash）都算"模型答的"，只有 rule 才是兜底 */
.ai-engine.model {
    background: #e8f2ff;
    color: #2b6cb0;
}

.ai-close {
    margin-left: auto;
    border: none;
    background: none;
    font-size: 20px;
    line-height: 1;
    cursor: pointer;
    color: #999;
}

.ai-tip {
    padding: 8px 12px;
    background: #fff8e6;
    color: #8a6d1b;
    font-size: 13px;
    display: flex;
    align-items: center;
}

.ai-tip-btn {
    margin-left: auto;
    border: 1px solid #8a6d1b;
    background: none;
    color: #8a6d1b;
    border-radius: 4px;
    padding: 2px 8px;
    cursor: pointer;
}

.ai-body {
    flex: 1;
    overflow-y: auto;
    padding: 10px 12px;
}

.ai-hint {
    color: #999;
    font-size: 13px;
}

.ai-msg {
    margin-bottom: 10px;
    clear: both;
}

.ai-msg.user {
    text-align: right;
}

.ai-msg.user .ai-text {
    background: #eb002a;
    color: #fff;
}

.ai-msg.assistant .ai-text {
    background: #f4f4f5;
    color: #333;
}

.ai-text {
    display: inline-block;
    max-width: 92%;
    padding: 7px 10px;
    border-radius: 8px;
    text-align: left;
    white-space: pre-wrap;
    word-break: break-word;
}

.ai-steps {
    margin: 4px 0 0;
    font-size: 12px;
    color: #aaa;
}

.ai-draft {
    margin-top: 6px;
    padding: 8px 10px;
    border: 1px solid #ffd7dd;
    border-radius: 8px;
    background: #fff7f8;
}

.ai-draft-line {
    margin: 2px 0;
    color: #333;
}

.ai-draft-btn {
    margin-top: 6px;
    width: 100%;
    border: none;
    border-radius: 6px;
    background: #eb002a;
    color: #fff;
    padding: 7px 0;
    cursor: pointer;
}

.ai-draft-note {
    margin: 6px 0 0;
    font-size: 12px;
    color: #999;
}

.ai-foot {
    display: flex;
    padding: 8px;
    border-top: 1px solid #eee;
}

.ai-input {
    flex: 1;
    border: 1px solid #ddd;
    border-radius: 6px 0 0 6px;
    padding: 7px 9px;
    outline: none;
}

.ai-send {
    border: none;
    border-radius: 0 6px 6px 0;
    background: #333;
    color: #fff;
    padding: 0 14px;
    cursor: pointer;
}

.ai-send:disabled {
    background: #bbb;
    cursor: not-allowed;
}
</style>
