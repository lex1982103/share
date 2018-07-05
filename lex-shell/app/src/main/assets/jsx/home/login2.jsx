class Main extends React.Component {
    constructor() {
        super()
        this.state = {}
    }
    componentDidMount() {
        MF.setTitle("新华人寿")
    }
    login() {
        APP.login(this.refs.loginName.value, this.refs.password.value, r => {
            MF.setEnv("userKey", r.userKey)
            MF.navi("home/home.html")
        }, r => {
            this.setState({ login: "fail" })
        })
    }
    render() {
        return (
            <div style={{height:"100%"}}>
                <div style={{height:"500px", background:"url('../images/logintitle.jpg')", textAlign:"center", verticalAlign:"middle"}}>
                    <img style={{margin:"100px auto auto auto", borderRadius:"150px", width:"300px", height:"300px", border:"10px solid white", boxShadow:"0 0 5px rgba(0, 198, 255,.6)"}} src="../images/loginhead.jpg"/>
                </div>
                <div className="form-item text16">
                    <div className="form-item-label" style={{width:"100px"}}>账号</div>
                    <div className="form-item-widget">
                        <input className="mt-1" ref="loginName" defaultValue="test" placeholder="请输入账号"/>
                    </div>
                </div>
                <div className="form-item text16">
                    <div className="form-item-label" style={{width:"100px"}}>密码</div>
                    <div className="form-item-widget">
                        <input className="mt-1" ref="password" type="password" defaultValue="123456" placeholder="请输入密码"/>
                    </div>
                </div>
                { this.state.login == "fail" ? <div className="ml-2 mt-1 tc-red text14">账号或密码错误</div> : null }
                <div className="btn-fl text18 tc-white bg-primary" onClick={this.login.bind(this)}>登录</div>
            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})