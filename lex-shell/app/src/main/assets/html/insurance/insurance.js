class Insurance extends React.Component {
    constructor() {
        super();
        this.state = {
            edit: false,
            applicant: []

        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("投保单");
        this.fetchClientList();
    }
    fetchClientList(){
        APP.openApply('/order/list.json', {type: '2', from: 0, number: 10},(res) => {
            this.setState({
                applicant: res.list
            })
        })
    }
    /*编辑操作*/
    editClient (data) {}
    /*获取性别函数*/
    getSex(code) {
        return code == "M"? "男" : "女";
    }
    render(){
        const {
            edit
        } = this.state;
        return (
            <div className="client-container">
                <div className="c-list">
                    <div className="cl-title">
                        <h3>当前页<i>{this.state.applicant && this.state.applicant.length}人</i></h3>
                        <a className="cl-edit" onClick={()=>this.setState({edit: !edit})}>{edit ? '取消': '编辑'}</a>
                    </div>
                    {
                        this.state.applicant.map(item=>{
                            return (
                                <dl className="cl-section">
                                    <dd>
                                        <a>
                                            <span>{item.productName || '无'}</span>
                                            <i>{item.applyNo || '无'}</i>
                                            <em>{item.createTime || '无'}</em>
                                        </a>
                                        {
                                            this.state.edit && (
                                                <span>
                                                   {/* <a onClick = {() => {this.editClient(item)}}>编辑</a>*/}
                                                    <a href="../receipt/receipt.html"
                                                      /* onClick = {() => {window.MF && MF.navi("../receipt/receipt.html");}}*/>回执</a>
                                                    <a href="../xinhua_lx/autograph_xh.html"
                                                      /* onClick = {() => {window.MF && MF.navi("../xinhua_lx/autograph_xh.html");}}*/>预览</a>
                                                    <a href="../xinhua_lx/notice_xh.html"
                                                       /*onClick = {() => {window.MF && MF.navi("../xinhua_lx/notice_xh.html");}}*/>通知</a>
                                                </span>
                                            )
                                        }
                                    </dd>
                                </dl>

                            )
                        })
                    }
                </div>
            </div>
        )
    }
}


ReactDOM.render(<Insurance/>, document.getElementById("root"))
