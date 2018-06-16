var Main = React.createClass({
    getInitialState() {
        return {
            orderId: common.param("orderId"),
            genderDict: {"M":"男", "F":"女"},
            nationDict: {},
            marriageDict: {},
            certTypeDict: {},
            relationDict: {"00":"本人", "01":"夫妻"},
            index: 0,
            mode: 0,
            cust: [{}]
        }
    },
    componentDidMount() {
        MF.setTitle("被保险人")

        APP.dict("cert,marriage,relation", r => {
            this.setState({
                nationDict: r.nation,
                certTypeDict: r.cert,
                relationDict: r.relation,
                marriageDict: r.marriage
            })
        })

        APP.apply.view(this.state.orderId, r => {
            let cust = r.detail ? r.detail.insurants : null
            this.setState({ cust: cust ? cust : [{}] })
        })
    },
    save() {
        let c = this.state.cust[this.state.index]

        if (this.state.mode == 1) {
            c.name = this.refs.name.value
            c.certNo = this.refs.certNo.value
            c.mode1 = true
        } else if (this.state.mode == 2) {
            c.mode2 = true
        } else if (this.state.mode == 3) {
            c.mode3 = true
        } else if (this.state.mode == 4) {
            c.mode4 = true
        }

        this.state.cust[this.state.index] = c
        APP.apply.save({ id: this.state.orderId, detail: { insurants: this.state.cust } }, v => {
            this.setState({ mode: 0, cust: this.state.cust })
        })
    },
    close() {
        MF.back()
    },
    render() {
        let cust = this.state.cust[this.state.index];
        return (
            <div>
                { list.map((v, i) => {
                    <div class="list-item" onClick="addToPlan" key="{{i}}">
                        <div class="list-item-icon">
                            <img src="{{v.icon}}"></img>
                        </div>
                        <div class="list-item-content">
                            <div class="text18" style="height:45rpx; line-height:45rpx;">{{v.name}}</div>
                                <div class="text12" style="height:35rpx; line-height:35rpx; color:gray;">{{v.name}}</div>
                            </div>
                        </div>
                    </div>
                )}
         </div>
		)
    }
})

$(document).ready( function() {
	ReactDOM.render(
		<Main/>, document.getElementById("root")
	);
});