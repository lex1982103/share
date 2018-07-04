class ProductDetail extends React.Component {
    constructor() {
        super();
        this.state = {}
    }
    componentDidMount (){

    }
    render(){
        return (
            <div className="product-container">
                <div className="c-list">
                    <div className="cl-title">
                    </div>
                </div>
            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<ProductDetail/>, document.getElementById("root"))
}
