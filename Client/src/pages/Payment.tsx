import React from "react";
import Navbar from "../components/navbar/Navbar";

type Props = {};

function Payment({}: Props) {
  return (
    <div>
      <Navbar></Navbar>

      <div style={{ display: "flex", justifyContent: "center", padding: 40 }}>
        {/* Checkout Form */}
        <div
          style={{
            background: "#fff",
            padding: 32,
            borderRadius: 10,
            boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
            width: 400,
          }}
        >
          <h2 style={{ textAlign: "center" }}>Secure Checkout</h2>
          <p style={{ textAlign: "center", color: "#888" }}>
            Choose your preferred payment method
          </p>
          {/* Payment Methods */}
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              gap: 16,
              margin: "16px 0",
            }}
          >
            <button>💳</button>
            <button>💳</button>
            <button>💳</button>
            <button>Al Contado</button>
            <button>Tarjeta de Débito</button>
            <button>Tarjeta de Crédito</button>
          </div>
          {/* Card Details */}
          <div>
            <label>Cardholder Name</label>
            <input
              type="text"
              placeholder="Name"
              style={{ width: "100%", marginBottom: 8 }}
            />
            <label>Card Number</label>
            <input
              type="text"
              placeholder="**** **** **** 1111"
              style={{ width: "100%", marginBottom: 8 }}
            />
            <div style={{ display: "flex", gap: 8 }}>
              <div style={{ flex: 1 }}>
                <label>Expiry date</label>
                <input
                  type="text"
                  placeholder="MM/YY"
                  style={{ width: "100%" }}
                />
              </div>
              <div style={{ flex: 1 }}>
                <label>CVV2</label>
                <input
                  type="text"
                  placeholder="CVV2"
                  style={{ width: "100%" }}
                />
              </div>
            </div>
          </div>
          {/* Billing Details */}
          <div style={{ marginTop: 16 }}>
            <label>E-mail</label>
            <input
              type="email"
              placeholder="E-mail"
              style={{ width: "100%", marginBottom: 8 }}
            />
            <label>Country</label>
            <select style={{ width: "100%" }}>
              <option>United Kingdom</option>
              <option>Peru</option>
              <option>USA</option>
              {/* ...otros países */}
            </select>
          </div>
          <button
            style={{
              width: "100%",
              marginTop: 24,
              background: "#2ecc40",
              color: "#fff",
              padding: 12,
              border: "none",
              borderRadius: 5,
            }}
          >
            Place Order
          </button>
          <div style={{ textAlign: "center", marginTop: 8 }}>
            <a href="#">Go Back</a>
          </div>
        </div>
        {/* Order Summary */}
        <div
          style={{
            marginLeft: 32,
            width: 250,
            background: "#f9f9f9",
            padding: 24,
            borderRadius: 10,
          }}
        >
          <h3>Your Order</h3>
          <div>
            <div>Test Product</div>
            <div>€ 50.00</div>
            <div>Quantity: 1</div>
          </div>
          <hr />
          <div>Subtotal: € 50.00</div>
          <div>Promo Code Applied: € 0.00</div>
          <div>Shipping & Handling: € 0.00</div>
          <div>Estimated Tax: € 0.00</div>
          <hr />
          <div style={{ fontWeight: "bold" }}>Order Total: € 50.00</div>
        </div>
      </div>
    </div>
  );
}

export default Payment;
