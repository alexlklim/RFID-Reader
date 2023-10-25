import Header from "./component/header/Header";
import Products from "./component/products/Products";
import FullProduct from "./component/products/FullProduct";
import './styles/component/main.scss';
import EmptyProduct from "./component/products/EmptyProduct";

import React, {Component} from 'react';
import ClearSessionDialog from "./component/dialogs/ClearSessionDialog";
import DeleteProductDialog from "./component/dialogs/DeleteProductDialog";
import BuyProductsDialog from "./component/dialogs/BuyProductsDialog";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            showFullProduct: false,
            fullProduct: null,
            productNumberToDelete: null,

            isClearSessionDialogOpen: false,
            isDeleteProductDialogOpen: false,
            isBuyProductsDialogOpen: false
        }

        this.onShowProduct = this.onShowProduct.bind(this)
        this.onHideProduct = this.onHideProduct.bind(this)
        this.openClearSessionDialog = this.openClearSessionDialog.bind(this);
        this.closeClearSessionDialog = this.closeClearSessionDialog.bind(this)

    }

    render() {
        return (
            <div className="App">
                <Header />
                {this.state.isClearSessionDialogOpen && (
                    <ClearSessionDialog onClose={this.closeClearSessionDialog}
                                        onHideProduct={this.onHideProduct}/>)}
                {this.state.isDeleteProductDialogOpen && (
                    <DeleteProductDialog onClose={this.closeDeleteProductDialog}
                                         productNumber={this.state.productNumberToDelete}
                                         onHideProduct={this.onHideProduct}/>)}
                {this.state.isBuyProductsDialogOpen && (
                    <BuyProductsDialog onClose={this.closeBuyProductsDialog}
                                       onHideProduct={this.onHideProduct}/>)}


                <div className="content">
                    <Products onShowProduct={this.onShowProduct}
                              onOpenDeleteProductDialog={this.openDeleteProductDialog}/>
                    {this.state.showFullProduct ? (
                        <FullProduct
                            product={this.state.fullProduct}
                            onShowProduct={this.onShowProduct}
                            onOpenDeleteProductDialog={this.openDeleteProductDialog}
                            onOpenBuyProductsDialog={this.openBuyProductsDialog}/>)
                        : (<EmptyProduct  onOpenBuyProductsDialog={this.openBuyProductsDialog} />)}
                </div>
            </div>
        );
    }




    onShowProduct(productNumber){
        this.setState({fullProduct: productNumber})
        this.setState({showFullProduct: true})
        this.getFullProduct(productNumber)
        console.log("onShowProduct");
    }
    onHideProduct = () => {
        this.setState({fullProduct: {}})
        this.setState({showFullProduct: false})
    };



    openClearSessionDialog = () => {
        this.setState({ isClearSessionDialogOpen: true });
    };
    closeClearSessionDialog = () => {
        this.setState({ isClearSessionDialogOpen: false });
    };


    openDeleteProductDialog = (productNumber) => {
        this.setState({ productNumberToDelete: productNumber });
        this.setState({ isDeleteProductDialogOpen: true });
    }
    closeDeleteProductDialog = () => {
        this.setState({ productNumberToDelete: null });
        this.setState({ isDeleteProductDialogOpen: false });
    }

    openBuyProductsDialog = () => {
        this.setState({ isBuyProductsDialogOpen: true });
    };
    closeBuyProductsDialog = () => {
        this.setState({ isBuyProductsDialogOpen: false });
    };



    getFullProduct(productNumber) {
        const fetchProductData = () => {
            fetch(`http://localhost:9091/api/product/item/${productNumber}`)
                .then(response => response.json())
                .then(data => {
                    this.setState({ fullProduct: data });
                    console.warn('Products data fetched');
                })
                .catch(error => console.error('Error fetching products data:', error));
        };
        fetchProductData();
    }




}

export default App;