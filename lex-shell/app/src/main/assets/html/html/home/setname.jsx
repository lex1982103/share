class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            
        }
    }
    render() {
        return (
            <div className="setName-wrap">
                <div>
                    <input type="text" placeholder="请输入名称"/>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
