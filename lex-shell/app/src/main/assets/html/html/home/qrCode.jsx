class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            
        }
    }
    render() {
        return (
            <div className="qr-code-wrap">
                <div className="qr-code-box">
                    <h6>我的专属名品二维码</h6>
                    <div>
                        <img src="../images/mine/myqrcode.png" alt=""/>
                    </div>
                    <p>扫一扫或右上角直接分享</p>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
