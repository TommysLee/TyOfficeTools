// 初始化Vue
const app = Vue.createApp({
  data() {
    return {
      title: '满江红',
      author: "岳飞"
    }
  },
  mounted() {
  },
  methods: {
  }
});
const appInstance = app.mount('#app');