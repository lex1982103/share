class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            orderId: common.param("orderId"),
        }
    }
    componentDidMount() {
        MF.setTitle("影像")

        try {
            let cardData = JSON.parse(localStorage.cardData)
            alert(cardData)
            this.setState({
                cardData:cardData
            })
        } catch (err) {
        }
    }
    next() {
            // MF.navi("apply/preview.html?orderId=" + this.state.orderId)
            window.MF &&  MF.navi("xinhua_lx/autograph_xh.html?orderId=" + this.state.orderId)

    }
    render() {
        return (
            <div>
                <div style={{overflow:'hidden'}}>
                    {
                        this.state.cardData && this.state.cardData.map((prod)=>{
                            return(
                                <img src={prod} style={{width:'50%',height:'20%',float:'left'}}/>
                            )
                        })
                    }
                </div>

                <div className="bottom text18 tc-primary">
                    <div className="ml-3 mr-auto">
                    </div>
                    <div className="mr-3" onClick={this.next.bind(this)}>
                        预览
                    </div>
                </div>
            </div>
		)
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})