import React, {Component} from 'react';
import '../../styles/component/dialogs.scss';

class BuyProductsDialog extends Component {

    buyAndCloseDialog = () => {
        this.props.onHideProduct();
        this.clearSession();
        this.props.onClose();
    }
    render() {
        return (
            <div className="dialog-overlay">
                <div className="dialog-window">
                    <h2>Czy na pewno chcesz dokonać zakupu? </h2>
                    <div className="buttons">
                        <button onClick={this.buyAndCloseDialog} >Tak</button>
                        <button onClick={this.props.onClose}>Nie</button>
                    </div>
                </div>
            </div>
        );
    }

    clearSession() {
        fetch('http://localhost:9091/api/product/item/clear', {
            method: 'GET',
        }).then(response => {
                if (response.status === 204) {console.log("Session cleared successfully");
                } else {console.error("Failed to clear the session");}
            }).catch(error => {console.error('Error clearing session:', error);});
        fetch('http://localhost:9091/api/rfid/clear', {
            method: 'GET',
        }).then(response => {
            if (response.status === 204) {console.log("Session cleared successfully");
            } else {console.error("Failed to clear the session");}
        }).catch(error => {console.error('Error clearing session:', error);});
    }
}
export default BuyProductsDialog;