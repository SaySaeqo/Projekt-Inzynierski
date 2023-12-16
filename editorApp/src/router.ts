import { createRouter, createWebHashHistory } from "vue-router";
import TheList from "@/components/TheList.vue";
import TheEditor from "@/components/TheEditor.vue";
import TheLogin from "@/components/TheLogin.vue";
import firebaseApp from "@/initfirestore";
import { getAuth } from "firebase/auth";

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      component: TheList,
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: "/exhibit/:id",
      component: TheEditor,
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: "/login",
      component: TheLogin,
    },
  ],
});

const auth = getAuth(firebaseApp);
router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
  const isAuthenticated = auth.currentUser;
  if (requiresAuth && !isAuthenticated) {
    next("/login");
  } else {
    next();
  }
});

export default router;
