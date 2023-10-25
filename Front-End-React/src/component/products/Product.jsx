import React, {Component} from 'react';
import '../../styles/component/product/product.scss';
import { MdDelete } from 'react-icons/md';
class Product extends React.Component {

    render() {
        return (
            <div className="product" onClick={() => this.props.onShowProduct(this.props.product.productNumber)}>
                <div className="col col1">
                    <h3>{this.props.product.title}</h3>
                    <p>{this.props.product.specification}</p>
                </div>
                <div className="col col2">
                    <div className="amount">
                        {this.props.product.amount}
                    </div>
                </div>
                <div className="col col3">
                    <p>{this.props.product.priceNet} zł</p>
                </div>
                <div className="col col4">
                    <MdDelete className="delete"/>
                </div>
            </div>
        );
    }
}

export default Product;