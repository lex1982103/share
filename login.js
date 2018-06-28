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
            // MF.navi("home/home.html")
            // APP.login(this.state.loginName, this.state.passWord, r => {
            //     // MF.setEnv("userKey", r.userKey)
            //     // MF.navi("home/home.html")
            // }, r => {
            //     this.setState({ login: "fail" })
            //     MF.navi("home/home.html")
            // })
            ajax('/user/login.json', {
                "loginName":this.refs.loginName.value,
                "password":this.refs.password.value
            },(data)=>{
                if(data.result == 'success'){
                    location.href = 'home.html'
                }else{
                    alert('登录出错！')
                }
                // ajax('/customer/list.json',{
                //     "from":0,
                //     "number":10
                // },(res)=>{
                //     console.log(res,'res')
                // })
            })
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
