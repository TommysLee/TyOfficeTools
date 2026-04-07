// 初始化Vue
const app = Vue.createApp({
  data() {
    return {
      title: 'LoopRowTableRenderPolicy 是一个特定场景的插件，根据集合数据循环表格行！',
      cols: ['景点', '级别'],
      rows: ['蓬莱阁', '鹳雀楼']
    }
  },
  mounted() {
  },
  methods: {
  }
});
const appInstance = app.mount('#app');