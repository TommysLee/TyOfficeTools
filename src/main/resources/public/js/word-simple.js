// 初始化Vue
const app = Vue.createApp({
  data() {
    return {
      data: {
        title: 'Hi, poi-tl Word模板引擎',
        link: "https://www.baidu.com",
        linkText: "百度官网链接",
        pic: "https://www.baidu.com/img/flexible/logo/pc/result.png",
        cols: ['景点', '级别'],
        rows: [['黄鹤楼', 'AAAAA']],
        list: ['黄鹤楼', '岳阳楼', '滕王阁', '鹳雀楼', '蓬莱阁']
      }
    }
  },
  mounted() {
  },
  methods: {
  }
});
const appInstance = app.mount('#app');