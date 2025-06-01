 import { useEffect, useState } from 'react';
 import axios from 'axios';
 import ProductCard from '../components/ProductCard';

 const ProductList = () => {
   const [products, setProducts] = useState([]);

   const fetchProducts = async () => {
     const response = await axios.get('http://localhost:8080/api/products');
     setProducts(response.data);
   };

   useEffect(() => {
     fetchProducts();
   }, []);

   return (
     <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
       {products.map(product => (
         <ProductCard key={product.id} product={product} fetchProducts={fetchProducts} />
       ))}
     </div>
   );
 };

 export default ProductList;
