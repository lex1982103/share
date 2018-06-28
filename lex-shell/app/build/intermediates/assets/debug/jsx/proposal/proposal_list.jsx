class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            total: 0,
            list: []
        }
    }
    componentDidMount() {
        MF.setTitle("我的建议书")
        APP.proposal.query(r => {
            MF.setTitle("我的建议书（" + r.total + "）");
            this.setState({ list: r.list, total: r.total })
        })
    }
    create() {
        MF.navi("proposal/start.html")
    }
    open(proposalId) {
        MF.navi("proposal/start.html?proposalId=" + proposalId)
    }
    close() {
        APP.back()
    }
    render() {
        return (
            <div>
                <div className="btn-fl text18 tc-white bg-primary" onClick={this.create.bind(this)}>新的建议书</div>
                { this.state.list.map((v, i) =>
                    <div className="list-item" onClick={this.open.bind(this, v.id)} key={i}>
                        <div className="list-item-icon">
                            <img src="../images/proposal.png"></img>
                        </div>
                        <div className="list-item-content">
                            <div className="text18" style={{height:"45px", lineHeight:"45px"}}>{ v.name == null ? "我的建议书" : v.name }</div>
                            <div className="text12" style={{height:"35px", lineHeight:"35px", color:"gray"}}>{ v.remark == null ? (v.tag == "single" ? "单人计划" : "多人计划") : v.remark }</div>
                        </div>
                    </div>
                )}
            </div>
		)
    }
}

$(document).ready(() => {
	ReactDOM.render(<Main/>, document.getElementById("root"))
})