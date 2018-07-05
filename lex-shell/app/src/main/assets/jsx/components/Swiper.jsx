
export default class PolySwiper extends React.Component{
    constructor(props){
        super(props);
        this.state={
            images: props.images || [],
            initialized: false
        }
    }

    componentDidMount(){
        this.initSwiper();
    }
    initSwiper(){
        if(this.state.images.length > 0 && !this.state.initialized){
            var mySwiper = new Swiper ('.swiper-container', {
                loop: true,
                pagination: {
                    el: '.swiper-pagination',
                    dynamicBullets: true,
                },
            });
            this.setState({
                initialized: true
            })
        }

    }
    componentWillReceiveProps(nextProps) {
        if(nextProps['images'] !== undefined && nextProps['images'] !== null ) {
            this.setState({
                images: nextProps.images || '',
            }, function () {
                this.initSwiper();
            });
        }
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