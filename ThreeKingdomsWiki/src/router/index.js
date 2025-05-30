import { createWebHistory, createRouter } from "vue-router";

import mainpage from "../MainPage.vue";
import HanIkzu from "../components/HanIkzu.vue";




const routes = [
  {
    path: "/",
    component: mainpage,
  },
  {
    path: "/HanIkzu",
    component: HanIkzu,
  },

];

const router = createRouter({
  // 라우트 생성
  history: createWebHistory(),
  routes,
});
export default router;
