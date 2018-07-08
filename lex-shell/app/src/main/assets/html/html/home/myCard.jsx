class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            myCardMsglist: [
                {
                    name: '头像',
                    link: 'xxxx',
                    msg: '',
                    img: '../images/male.png'
                },
                {
                    name: '姓名',
                    link: './setname.html',
                    msg: '阿萨德',
                },
                {
                    name: '工号',
                    link: 'xxxx',
                    msg: '312115455'
                },
                {
                    name: '职级',
                    link: 'xxxx',
                    msg: '部门经理'
                },
                {
                    name: '电话号码',
                    link: 'xxxx',
                    msg: '184555444545'
                },
                {
                    name: 'QQ',
                    link: 'xxxx',
                    msg: 'QQ4546655'
                },
                {
                    name: '微信',
                    link: 'xxxx',
                    msg: 'zhu454545'
                },
                {
                    name: '公司地址',
                    link: 'xxxx',
                    msg: '北京朝阳门北大街14'
                },
                {
                    name: '公司网址',
                    link: 'xxxx',
                    msg: 'www://wwww.adffgh.cn'
                },
                {
                    name: '我的二维码',
                    link: './qrCode.html',
                    msg: '',
                    img: '../images/mine/mycart-ewm.png'
                }
            ]
        }
    }
    changeName (item) {
        // 更改姓名
        switch (item.name) {
            case "姓名":
                location.href = item.link
            break;
            case "我的二维码":
                location.href = item.link
            break;
        }
    }
    render() {
        return (
            <div className="myCard-wrap">
                <ul className="myCard-list">
                    {
                        this.state.myCardMsglist && this.state.myCardMsglist.map((item,index) => {
                            return (
                                <li key={index} onClick={this.changeName.bind(this,item)}>
                                    <p>{item.name}</p>
                                    <p>
                                        <span>{item.msg}</span>
                                        <span>
                                            {
                                               item.img ?  
                                                <img src={item.img} alt="xx"/>
                                               : ''
                                            }
                                            <b>＞</b>
                                        </span>
                                    </p>
                                </li>
                            )
                        })
                    }
                </ul>
                {/* <div className="myCard-mark">
                    <div className="myCard-box">
                        <div className="change-name">
                            <input type="text" placeholder="请输入要更改的名字"/>
                        </div>
                        <div className="myCard-qr-code">
                            <h6>我的专属名片二维码</h6>
                            <img src="" alt=""/>
                        </div>
                    </div>
                </div> */}
            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
