class Main extends React.Component {
    constructor() {
        super()
    }
    componentDidMount() {
        MF.setTitle("首页")
    }
    openApply(orderId) {
        MF.navi("apply/start.html?orderId=" + orderId)
    }
    newApply() {
        MF.navi("apply/start.html")
    }
    render() {
        return (
            <div>
                <div className="btn-fl text18 tc-white bg-primary" onClick={this.newApply.bind(this)}>新的投保</div>
                <div className="btn-fl text18 tc-white bg-primary" onClick={this.openApply.bind(this, 40066)}>打开测试投保单</div>
            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})