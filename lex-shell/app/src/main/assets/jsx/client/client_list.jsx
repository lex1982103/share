class ClientList extends React.Component {
    constructor() {
        super();
        this.state = {
            // clientCount: 4,
            clientList: [],
            mockData: [],
            pageNumber: 1,
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("客户管理");
        this.fetchClientList();

    }
    fetchClientList(){
        /** 按首字母分组
         * [{
         *     A:[ {
         *       name: ''
         *     }]
         * }]
         * */
        APP.list("/customer/list.json",{ from:0, number:20 },r => {
            this.setState({mockData: r.list}, () => {
                const data = this.state.mockData.map(d=>{
                    let pinyinStr = pinyinUtil.getFirstLetter(d.name, false);
                    let firstAlpha = pinyinStr.substring(0, 1);
                    // console.log('speel', firstAlpha.toUpperCase());
                    d.alpha = firstAlpha.toUpperCase();
                    return d;
                })
                /** 按首字母排序 */
                for(let i = 0; i < data.length; i++){
                    for(let j = i+1; j < data.length; j++){
                        if(data[i].alpha > data[j].alpha){
                            let temp = data[i];
                            data[i] = data[j];
                            data[j] = temp;
                        }
                    }
                }
                /**根据字母分组*/
                let sortArr = [];
                data.map(d=>{
                    // 检查该字母是否已处理过
                    if(!sortArr.filter(item=>item.alpha === d.alpha).length){
                        let alphaObj = {
                            alpha: d.alpha
                        };
                        let arr = data.filter(item=>item.alpha === d.alpha);
                        alphaObj.list = arr;
                        sortArr.push(alphaObj);
                    }

                });
                this.setState({
                    clientList: sortArr
                })
            })
        })
    }
    onAlphaClick(id){
        this.props.onAlphaClick && this.props.onAlphaClick(key);
        let el=document.getElementById(id);
        el.scrollIntoView();
    }
    /*编辑客户操作*/
    editClient (data) {
        APP.list('/customer/view.json', {"customerId":data.id,}, r => {
            window.MF && MF.navi("client/create_client.html?customerMsg=" + JSON.stringify(r));
        })
    }
    /*删除客户操作*/
    deleteClient (data) {
        APP.alert("注意", "确定删除吗？", r => {
                 APP.list('/customer/delete.json', {"customerId":data.id,}, r => {
                    this.fetchClientList();//刷新
                })
            }, r => {})
    }
    /*获取性别函数*/
    getSex(code) {
        return code == "M"? "男" : "女";
    }
    render(){
        return (
            <div className="client-container">
                <div className="remind-wrap">
                    <a className="remind-birthday">
                        <img src="../images/client/remind-birthday.png"/>
                        <span>生日提醒</span>
                        <em>2</em>
                    </a>
                    <span></span>
                    <a className="remind-renew">
                        <img src="../images/client/remind-renew.png"/>
                        <span>续期提醒</span>
                    </a>
                </div>
                <div className="c-list">
                    <div className="cl-title">
                        <h3>当前页客户<i>{this.state.mockData && this.state.mockData.length}人</i></h3>
                    </div>
                    {
                        this.state.clientList.map(item=>{
                            return (
                                <dl className="cl-section list-group-item" id={'sec'+item.alpha}>
                                    <dt>{item.alpha}</dt>
                                    {
                                        item.list.map(c=>{
                                            return (

                                                <dd>
                                                    <a>
                                                        <span>{c.name}</span>
                                                        <i>{this.getSex(c.gender)}</i>
                                                        <em>{c.birthday}</em>
                                                    </a>
                                                    <span>
                                                        <a onClick = {() => {this.editClient(c)}}>编辑</a>
                                                        <a onClick = {() => {this.deleteClient(c)}}>删除</a>
                                                    </span>
                                                    <span className="cl-line"></span>
                                                </dd>

                                            )
                                        })
                                    }

                                </dl>

                            )
                        })
                    }
                </div>
                <div className="c-alphabet">
                    {
                        this.state.clientList.map(c=>{
                            return (
                                <a href="javascript:void(0)" onClick={()=>this.onAlphaClick('sec' + c.alpha)}>{c.alpha}</a>
                            )
                        })
                    }

                </div>
                <div className="c-footer">
                    <a onClick = {() => {
                        window.MF && MF.navi("client/create_client.html?customerMsg=" + JSON.stringify({}));
                    }}>新建客户</a>
                </div>
            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<ClientList/>, document.getElementById("root"))
})