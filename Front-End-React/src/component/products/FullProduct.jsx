import React, {Component} from 'react';
import '../../styles/component/product/full_product.scss';
import terminal from "../../styles/img/terminal.png";
import { MdDelete } from 'react-icons/md';
import {BiSolidShoppingBag} from "react-icons/bi";


class FullProduct extends Component {
    render() {
        return (
            <div className="full-product">
                <div className="product-one">
                    <img src={this.props.product.image}  alt="product-photo"/>
                    <div className="product-info">
                        <h1>{this.props.product.title}</h1>
                        <p>{this.props.product.description}</p>
                        <div className="block">
                            <div className="block-price">
                                <p className="block-amount">
                                    <p>Szt:</p>
                                    <div className="amount">{this.props.product.amount}</div>
                                </p>
                                <div>
                                    <h2>Cena netto: </h2>
                                    <p>{this.props.product.priceNet} zł</p>
                                </div>
                                <div>
                                    <h2>Cena brutto: </h2>
                                    <p>{this.props.product.priceGross} zł</p>
                                </div>
                            </div>
                            <div className="block-delete"
                                 onClick={() => this.props.onOpenDeleteProductDialog(this.props.product.productNumber)}>
                                <MdDelete className="icon"/>
                            </div>
                        </div>
                    </div>
                </div>

                <button onClick={this.props.onOpenBuyProductsDialog}>
                    <p>Zapłać</p><BiSolidShoppingBag />
                </button>
            </div>
        );
    }
}

export default FullProduct;