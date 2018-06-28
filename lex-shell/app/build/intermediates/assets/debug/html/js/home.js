
class PolySwiper extends React.Component{
    constructor(props){
        super(props);
        this.state={
            images: props.images || []
        }
    }

    componentDidMount(){
        var mySwiper = new Swiper ('.swiper-container', {
            loop: true,

            pagination: {
                el: '.swiper-pagination',
                dynamicBullets: true,
            },
        })
    }
    render(){
        return (
            <div className="swiper-container">
                <div className="swiper-wrapper">
                    {
                        this.state.images.map((item,index)=>{
                            return (
                                <div className="swiper-slide" key={'banner'+index}><a><img src={item.url}/></a></div>
                            )
                        })
                    }
                </div>
                <div className="swiper-pagination"></div>
            </div>
        )
    }
}

ReactDOM.render(
    <div>
        <PolySwiper images={[
            {url: '../images/home/banner.jpg'},
            {url: '../images/home/banner.jpg'},
            {url: '../images/home/banner.jpg'}
        ]}/>
    </div>,
    document.getElementById('root')
);
