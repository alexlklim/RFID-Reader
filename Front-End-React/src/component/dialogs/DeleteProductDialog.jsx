import React, {Component} from 'react';
import '../../styles/component/dialogs.scss';

class DeleteProductDialog extends React.Component {



    clearProductAndCloseDialog = () => {
        this.props.onHideProduct();
        this.deleteProduct(this.props.productNumber);
        this.props.onClose();
    }

    render() {
        return (
            <div className="dialog-overlay">
                <div className="dialog-window">
                    <h2>Czy na pewno chcesz anulować pozycję? </h2>
                    <div className="buttons">
                        <button onClick={this.clearProductAndCloseDialog} >Tak</button>
                        <button onClick={this.props.onClose}>Nie</button>
                    </div>
                </div>
            </div>
        );
    }

    deleteProduct(productNumber) {
        fetch(`http://localhost:9091/api/product/item/delete/${productNumber}`, {
            method: 'GET',
        })
            .then(response => {
                if (response.status === 204) {console.log("Session cleared successfully");
                } else {
                    console.error("Failed to clear the session");
                }
            })
            .catch(error => {
                console.error('Error clearing session:', error);
            });
    }

}


export default DeleteProductDialog;