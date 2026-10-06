<template>
    <div>

    </div>
</template>
<script>
export default {
    name: 'empty',
    created() {
        console.log(this.$route.params.p);
        this.$router.replace({ path: this.$route.params.p })
    }
}
</script>