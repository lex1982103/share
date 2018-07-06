class Main extends React.Component {
    constructor() {
        super()
        this.state = {
            imgSrc:[
                '../images/introduce1.gif',
                '../images/introduce2.gif',
                '../images/introduce3.gif',
                '../images/introduce4.gif',
                '../images/introduce5.gif'
            ]
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("新华人寿")
    }
    render() {
        return (
            <div className="loginMain">
                <div className='PlayerMain'>
                    <video controls="controls" autoplay="autoplay">
                        <source src='../images/1530287674060056.mp4' type="video/mp4" />
                    </video>
                </div>
                <ul className="conpanyIn">
                    {
                        this.state.imgSrc.map((prod)=>{
                            return(
                                <li>
                                    <img src={prod} alt=""/>
                                </li>
                            )
                        })
                    }
                </ul>
            </div>
        )
    }
}

ReactDOM.render(<Main/>, document.getElementById("root"))
