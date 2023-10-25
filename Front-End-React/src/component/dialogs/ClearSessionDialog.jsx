import React, {Component} from 'react';
import '../../styles/component/dialogs.scss';

class ClearSessionDialog extends React.Component {

    clearAndCloseSession = () => {
        this.props.onHideProduct();
        // this.clearSession();
        this.props.onClose();
    }

    render() {
        return (
            <div className="dialog-overlay">
                <div className="dialog-window">
                    <h2>Czy na pewno chcesz anulować zakupy?</h2>
                    <div className="buttons">
                        <button onClick={this.clearAndCloseSession}>Tak</button>
                        <button onClick={this.props.onClose}>Nie</button>
                    </div>
                </div>
            </div>
        );
    }


    // clearSession() {
    //     fetch('http://localhost:9094/api/product/clear', {
    //         method: 'POST',
    //     })
    //         .then(response => {
    //             if (response.status === 204) {
    //                 console.log("Session cleared successfully");
    //             } else {
    //                 console.error("Failed to clear the session");
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error clearing session:', error);
    //         });
    // }

}


export default ClearSessionDialog;