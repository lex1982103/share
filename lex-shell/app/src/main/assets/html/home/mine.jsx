class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            userMsglist: [
                {
                    name: '客户管理',
                    icon: '../images/mine/khgl.png',
                    link: '../client/client_list.html'
                },
                {
                    name: '我的建议书',
                    icon: '../images/mine/wdjys.png',
                    link: '../proposal/start.html'
                },
                {
                    name: '投保单',
                    icon: '../images/mine/tbd.png',
                    link: '../insurance/insurance.html'
                },
                {
                    name: '电子保单',
                    icon: '../images/mine/dzbd.png',
                    link: 'xxxx'
                },
                {
                    name: '投保进度查询',
                    icon: '../images/mine/tbjdcx.png',
                    link: 'xxxx'
                },
                {
                    name: '在线保全',
                    icon: '../images/mine/xzbq.png',
                    link: 'xxxx'
                },
                {
                    name: '理赔服务',
                    icon: '../images/mine/lpfw.png',
                    link: 'xxxx'
                }
            ]
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("新华人寿")
    }
    listEnter (item) { // 列表点击
        switch (item.name) {
            case "客户管理":
                location.href = item.link
                break;
            case "我的建议书":
                location.href = item.link
                break;
            case "投保单":
                location.href = item.link
                break;
        }
    }
    myCard () {
        // 我的名片
        location.href = "./myCard.html";
    }
    render() {
        return (
            <div className="mine-wrap">
                <dl onClick={this.myCard.bind(this)} className="user-msg">
                    <dd>
                        <h6>演示的</h6>
                        <p>工号：<span>2121211</span></p>
                        <p>职级：<span>部门经理</span></p>
                    </dd>
                    <dt>
                        <img src="../images/male.png" alt="头像"/>
                    </dt>
                </dl>
                <ul className="user-list">
                    {
                        this.state.userMsglist && this.state.userMsglist.map((item,index) => {
                            return (
                                <li onClick={this.listEnter.bind(this,item)} key={index}>
                                    <p>
                                        <img src={item.icon} alt="出错"/>
                                    </p>
                                    <p>
                                        <span>{item.name}</span>
                                        <b>＞</b>
                                    </p>
                                </li>
                            )
                        })
                    }
                </ul>
            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
