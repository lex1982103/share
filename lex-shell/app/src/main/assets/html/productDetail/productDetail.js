class Main extends React.Component {
    constructor() {
        super()
        this.state = {
        }
    }
    componentDidMount() {
        let productData = JSON.parse(localStorage.productData)
        this.setState({
            productData:productData
        },()=>{
            console.log(productData,'productData')
        })
        window.MF && MF.setTitle("产品详情")
    }
    render() {
        return (
            <div>
                {
                    this.state.productData&&<div dangerouslySetInnerHTML={{__html:this.state.productData.content}}></div>
                }
            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
