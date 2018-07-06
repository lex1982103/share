class Main extends React.Component {
    constructor() {
        super()
        this.state = {

        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("新华人寿")
    }
    render() {
        return (
            <div className="mineMain">

            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
