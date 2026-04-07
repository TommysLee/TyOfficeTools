// 初始化Vue
const app = Vue.createApp({
  data() {
    return {
      list: [],
      que: {},
      editor: null
    }
  },
  computed: {
    listMap() {
      let map = {};
      for (let item of this.list) {
        map[item.id] = item;
      }
      return map;
    }
  },
  mounted() {
    this.list = this.loadStorageData();
    this.initEditor();
  },
  methods: {
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
    loadQue() {
      let param = Qs.parse(location.search.substring(1));
      if (param.id) {
        this.que = this.listMap[param.id] || {};
        this.editor.root.innerHTML = this.resolveHtml(this.que.html);
        console.log("加载数据", '\n', this.que);
      }
    },
    saveQue() {
      let html = this.editor.getSemanticHTML();
      let que = this.listMap[this.que.id];
      if (que) { // 更新
        que.html = html;
      } else { // 新增
        que = {
          id: Date.now(),
          html
        }
        this.list.push(que);
      }
      this.que = que;
      this.saveStorageData(this.list);
      console.log("保存数据", '\n', this.que);
      alert("保存成功")
    },
    renderLaTexToString(latex) {
      return katex.renderToString(latex, {
        throwOnError: false
      })
    },
    resolveHtml(html) {
      if (html) {
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, "text/html");
        const formulaNodes = doc.querySelectorAll(".ql-formula");
        for (let el of formulaNodes) {
          let latex = el.innerText;
          if (latex && latex?.length > 2) {
            latex = latex.substring(1, latex.length - 1);
            el.setAttribute('data-value', latex);
            el.innerHTML = '<span contenteditable="false">' + this.renderLaTexToString(latex) + '</span>';
          }
        }
        html = doc.body.innerHTML;
      }
      return html || '';
    },
    initEditor() {
      // 自定义工具栏
      const TOOLBAR_CONFIG = [
        ['undo', 'redo', 'clean', 'format-painter'],
        [
          { header: [1, 2, 3, 4, 5, 6, false] },
          { font: ['songti', 'yahei', 'kaiti', 'heiti', 'lishu', 'mono', 'arial', 'arialblack', 'comic', 'impact', 'times'] },
          { size: ['12px', '14px', '16px', '18px', '20px', '24px', '32px', '36px', '48px', '72px'] },
          { lineheight: ['1', '1.2', '1.5', '1.75', '2', '3', '4', '5'] },
        ],
        ['bold', 'italic', 'strike', 'underline', 'divider'],
        [{ color: [] }, { background: [] }],
        [{ align: '' }, { align: 'center' }, { align: 'right' }, { align: 'justify' }],
        [{ list: 'ordered' }, { list: 'bullet' }, { list: 'check' }],
        [{ script: 'sub' }, { script: 'super' }],
        [{ indent: '-1' }, { indent: '+1' }],
        [{ direction: 'rtl' }],
        ['link', 'blockquote', 'code', 'code-block'],
        ['image', 'better-table'],
        ['emoji', 'video', 'formula', 'fullscreen'],
      ];
      this.$nextTick(() => {
        // 初始化 TinyEditor 富文本编辑器
        this.editor = new FluentEditor('#editor', {
          theme: 'snow',
          modules: {
            toolbar: TOOLBAR_CONFIG,
            file: true,
            'emoji-toolbar': true,
            counter: true,
            i18n: {
              lang: 'zh-CN'
            }
          }
        })
        this.loadQue()
      })
    },
    forwardListPage() {
      window.location.href = "list.html"
    }
  }
});
const appInstance = app.mount('#app');