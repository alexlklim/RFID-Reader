import React, {Component} from 'react';
import Product from "./Product";
import '../../styles/component/product/products.scss';

class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {
            products: [],
            totalPriceNet: 0,
            totalPriceGross: 0,
            totalAmount: 0
        };
    }
    render() {
        return (
            <div className="products">
                <div className="header">
                    <p>Koszyk</p>
                </div>
                <div className="product-list">
                    {this.state.products.map((product, index) => (
                        <Product
                            key={product.id}
                            product={product}
                            onShowProduct={this.props.onShowProduct}
                        />
                    ))}
                </div>



                <div className="footer">
                    <div className="summary">
                        <p>Artykuły</p>
                        <p>{this.state.totalAmount} szt</p>
                    </div>
                    <div className="summary">
                        <p>Cena netto</p>
                        <p>{this.state.totalPriceNet} zł</p>
                    </div>
                    <div className="summary-line">
                        <p>Do zapłaty</p>
                        <p className="white-circle" >{this.state.totalPriceGross} zł</p>
                    </div>
                </div>
            </div>
        );
    }


    componentDidMount() {
        // Function to fetch products data
        const fetchProductsData = () => {
            fetch('http://localhost:9091/api/product/item/all')
                .then(response => response.json())
                .then(data => {
                    this.setState({ products: data });
                    console.warn('Products data fetched');
                })
                .catch(error => console.error('Error fetching products data:', error));
        };

        // Function to fetch total prices data
        const fetchTotalPricesData = () => {
            fetch('http://localhost:9091/api/product/item/total-prices')
                .then(response => response.json())
                .then(data => {
                    this.setState({
                        totalPriceNet: data.totalPriceNet,
                        totalPriceGross: data.totalPriceGross
                    });
                    console.warn('Total prices data fetched');
                })
                .catch(error => console.error('Error fetching total prices data:', error));
        };

        // Function to fetch total amount data
        const fetchTotalAmountData = () => {
            fetch('http://localhost:9091/api/product/item/total-amount')
                .then(response => response.json())
                .then(data => {
                    this.setState({ totalAmount: data.totalAmount });
                    console.warn('Total Amount fetched');
                })
                .catch(error => console.error('Error fetching total amount data:', error));
        };

        // Initial fetch
        fetchProductsData();
        fetchTotalPricesData();
        fetchTotalAmountData();

        // Set up an interval to fetch data every 1 second
        this.intervalId = setInterval(() => {
            fetchProductsData();
            fetchTotalPricesData();
            fetchTotalAmountData();
        }, 500); // 1000 milliseconds = 1 second
    }





}

export default Products;