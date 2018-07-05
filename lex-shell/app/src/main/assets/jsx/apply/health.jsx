class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        window.MF&&MF.setTitle("健康告知")
        APP.apply.view(this.state.orderId, r => {
            this.setState({ order: r })
        })
    }
    verify() {
        this.next()
    }
    getIdCardImg () {// 证件扫描
        this.setState({
            IdCardImg: {}
        })
    }
    next() {
        let everyState = JSON.parse(localStorage.everyState);
        let stateData = this.state;
        everyState.health = stateData;
        localStorage.everyState = JSON.stringify(everyState)
        if(window.MF){
            MF.navi("apply/beneficiary.html?orderId=" + this.state.orderId)
        }else {
            "apply/beneficiary.html?orderId=" + this.state.orderId
        }

    }

    getIdCardImg () {// 证件扫描
        this.setState({
            IdCardImg: {}
        })
    }
    render() {
        return (
            <div>
                <div className="text19 ml-auto mr-auto mt-3" style={{textAlign:"center"}}>
                    被保险人告知<br/>详细告知被保险人身体健康状况
                </div>
                <div className="ml-auto mr-auto mt-3" style={{textAlign:"center"}}>
                    <img style={{width:"203px", height:"158px"}} src="../images/healthverify.png"/>
                </div>
                <div className="mt-3">
                    <div className="btn-fl text18 tc-white bg-primary" onClick={this.verify.bind(this)}>开始录入</div>
                </div>
                <div className="tc-gray text14 ml-auto mr-auto mt-3" style={{textAlign:"center"}}>
                    整个过程不超过5分钟，且录入结果<br/>仅对告知有效，请认真录入信息
                </div>
                <div className="bottom text18 tc-primary">
                    <div className="form-item-widget">
                        <img className="mt-1" style={{width:"220px", height:"60px"}} src="../images/btn-scan.png" onClick={this.getIdCardImg.bind(this)}/>
                    </div>
                    <div className="ml-3 mr-0" style={{width:"300px"}}></div>
                    <div className="divx" onClick={this.next.bind(this)}>
                        <div className="ml-0 mr-0" style={{width:"390px", textAlign:"right"}}>
                            受益人
                        </div>
                        <div className="ml-1 mr-2" style={{width:"30px"}}>
                            <img className="mt-3" style={{width:"27px", height:"39px"}} src="../images/blueright.png"/>
                        </div>
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})