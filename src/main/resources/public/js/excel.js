// 初始化Vue
const app = Vue.createApp({
  data() {
    return {
      colsHeading: '空气监测',
      itemList: []
    }
  },
  mounted() {
    this.buildData();
  },
  methods: {
    random(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min + 1)) + min;
    },
    buildData() {
      let itemList = [];
      for (let i = 0; i < 24; i++) {
        itemList.push({
          time: '2025-11-11 ' + i + ':00',
          temp: this.random(27, 29),
          humi: this.random(15, 17),
          pm25: this.random(20,30)
        })
      }
      this.itemList = itemList;
    }
  }
});
const appInstance = app.mount('#app');