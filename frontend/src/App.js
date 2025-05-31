import { useState, useEffect } from 'react';
import ProductList from './components/ProductList';
import ProductForm from './components/ProductForm';

function App() {
  const [products, setProducts] = useState([]);

  const fetchProducts = async () => {
    const response = await fetch('http://localhost:8080/api/products');
    const data = await response.json();
    setProducts(data);
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-3xl font-bold text-center mb-6">E-Commerce Store</h1>
      <ProductForm fetchProducts={fetchProducts} />
      <ProductList products={products} fetchProducts={fetchProducts} />
    </div>
  );
}

export default App;
