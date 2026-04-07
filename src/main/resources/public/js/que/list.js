// 初始化Vue
const app = Vue.createApp({
  data() {
    return {
      school: '西格国际学校',
      message: '',
      list: []
    }
  },
  mounted() {
    this.doQuery()
  },
  methods: {
    doQuery() {
      this.list = this.loadStorageData();
      if (this.list.length < 1) {
        doAjax("/que/data", {}, result => {
          if (result.state) {
            this.list = result.data;
            this.saveStorageData(this.list);
          } else {
            console.log("获取后端数据失败...")
          }
          this.renderLaTex()
        })
      }
      setTimeout(() => {
        this.renderLaTex()
      }, 800)
    },
    loadStorageData() {
      let data = [];
      let dataJson = sessionStorage.getItem(DB_KEY);
      if (dataJson && dataJson.length > 10) {
        data = JSON.parse(dataJson);
      }
      return data;
    },
    saveStorageData(data) {
      let dataJson = JSON.stringify(data);
      sessionStorage.setItem(DB_KEY, dataJson);
    },
    renderLaTex(el) {
      this.$nextTick(() => {
        el = el || this.$refs.container;
        renderMathInElement(el, {
          delimiters: [
            {left: '$', right: '$', display: false},
          ]
        });
      })
    },
    exportWord(cols) {
      if (1 === cols || 2 === cols) {
        let url = "/que/export_word" + "?name=" + this.school + "&cols=" + cols;
        doAjax(url, this.list, result => {
          if (result.state) {
            this.message = "Word生成完毕，" + result.message + " 准备下载...";
            console.log(this.message, "\n", result);
            this.download(result.data)
          } else {
            alert("Word生成错误: " + result.message)
            console.log("Word生成错误: ", "\n", result.message);
          }
        })
        this.message = "正在生成Word..."
      } else {
        alert("参数错误_" + cols)
      }
    },
    download(uuid) {
      console.log("下载文件：", uuid)
      window.open("/que/download?uuid=" + uuid);
      setTimeout(() => {
        this.message = "";
      }, 8000)
    },
    forwardEditorPage() {
      window.location.href = "editor.html"
    }
  }
});
const appInstance = app.mount('#app');