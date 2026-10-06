import { createRouter, createWebHistory } from 'vue-router'


const routes = [
  {
    path: '/',
    name: 'index',
    component: () => import(/* webpackChunkName: "about" */ '../views/index.vue')
  },
  {
    path: '/films',
    name: 'films',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/filmsView.vue')
  },
  {
    path: "/cinemas",
    name: "cinemas",
    component: () => import('../views/cinemas.vue')
  },
  {
    path: "/movie",
    name: "movie",
    redirect:'/introduced',
    component: () => import('../views/movie.vue'),
    children: [{
      path: "/pic",
      name: "pic",
      component: () => import('../views/pic.vue')
    }, {
      path: "/actor",
      name: "actor",
      component: () => import('../views/actor.vue')
    }, {
      path: "/introduced",
      name: "introduced",
      component: () => import('../views/introduced.vue')
    }]
  },
  {
    path:'/xseats',
    name:"xseats",
    component:()=>import('../views/xseats.vue')
  },
  {
    path:'/payment',
    name:"payment",
    component:()=>import('../views/payment.vue')
  },
  {
    path:'/cinemashow',
    name:'cinemashow',
    component:()=>import('../views/cinemashow.vue')
  },
  {
    path:'/login',
    name:'Login',
    component:()=>import('../views/Login.vue')
  },
  {
    path:'/userinfo',
    name:'UserInfo',
    component:()=>import('../views/UserInfo.vue')
  },
  {
    path:'/order',
    name:'order',
    component:()=>import('../views/order.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  // document.title = `${to.meta.title} | maSYY`;
  document.title = `码上影院`;
  const loginUser = localStorage.getItem('loginUser');
  //未登录 且访问的不是登录组件  则跳转到登录组件
  if (!loginUser && to.path !== '/login') {
    next('/login');
  } else {
    next();
  }
});

export default router
