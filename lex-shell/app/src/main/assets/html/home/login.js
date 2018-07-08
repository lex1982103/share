class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            loginName:'',
            passWord:''
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("新华人寿")
    }
    loginData(inputValue){
        let e = window.event || arguments[0];
        if(inputValue == 'name'){
            this.setState({
                loginName:e.target.value
            },()=>{
                console.log(this.state.loginName)
            })
        }else{
            this.setState({
                passWord:e.target.value
            })
        }

    }
    login() {
        let that = this
        if(this.refs.loginName.value == ''){
            alert('登录账号不能为空！')
        }else if(this.refs.password.value == ''){
            alert('登录密码不能为空!')
        }else{
            /*APP.login(this.refs.loginName.value, this.refs.password.value, r => {
                MF.setEnv("userKey", r.content.userKey)
                MF.navi("home/home.html")
            }, r => {
                that.setState({ login: "fail" })
                // MF.navi("home/home.html")
            })*/
            let that = this
            $.ajax({
                contentType: 'application/json',
                type:'POST',
                url:"http://114.112.96.61:7666/app/user/login.json",
                data:JSON.stringify({
                    "loginName":this.refs.loginName.value,
                    "password":this.refs.password.value
                }),
                success(data){
                    if(window.MF){
                        localStorage.loginName = that.state.loginName
                        localStorage.channelId = data.content.channelId
                        localStorage.orgId = data.content.orgId
                        localStorage.userKey = data.content.userKey

                        MF.setEnv("userKey", data.content.userKey);
                        MF.setEnv("orgId", data.content.orgId);
                        MF.navi("home/home.html")

                    }else{
                        localStorage.loginName = that.state.loginName
                        localStorage.channelId = data.content.channelId
                        localStorage.orgId = data.content.orgId
                        localStorage.userKey = data.content.userKey
                        location.href = 'home.html'
                    }
                },
                error(err){
                    console.log(err,'err');
                }
            })
            /*ajax('/user/login.json', {
                "loginName":this.refs.loginName.value,
                "password":this.refs.password.value
            },(data)=>{

                if(data.result == 'success'){
                        MF.setEnv("userKey", data.userKey)
                        MF.navi("home/home.html")
                }else{
                    alert('登录出错！')
                }

            })*/

        }
    }
    render() {
        return (
            <div className="loginMain">
                <img src="../images/login/loginB.png" className="loginB"/>
                <div className="loginBox">
                    <span className="Title">新华保险</span>
                    <img src="../images/login/loginportrait.png" className="portrait"/>
                    <div className="loginNP">
                        <input type="text" ref="loginName" placeholder="请输入账号" onChange={this.loginData.bind(this,'name')} value={this.state.loginName}/>
                        <input type="password" ref="password" placeholder="请输入密码" onChange={this.loginData.bind(this,'password')} value={this.state.passWord}/>
                    </div>
                    <div className="loginB" onClick={this.login.bind(this)}>
                        登 录
                    </div>
                    <div className="forget">
                        <span>忘记密码？</span>
                    </div>
                </div>
                <div className="loginRegister">
                    <span>
                        还没有账号？点击<a href="javascript:void(0)">立即注册</a>
                    </span>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
