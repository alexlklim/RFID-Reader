import React, {Component} from 'react';
import '../../styles/component/product/empty_product.scss';
import { BiSolidShoppingBag } from 'react-icons/bi';
import info from "../../styles/img/info.png";



class EmptyProduct extends Component {
    render() {
        return (
            <div className="empty-product">
                <h1>
                    Wkładaj artykuły pojedynczo do kosza
                </h1>
                <img src={info} alt="logo"/>
                <button onClick={this.props.onOpenBuyProductsDialog}>
                    <p>Zapłać</p>
                    <BiSolidShoppingBag />
                </button>
            </div>
        );
    }
}

export default EmptyProduct;