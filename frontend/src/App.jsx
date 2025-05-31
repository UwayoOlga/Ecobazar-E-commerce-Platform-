 import { useState, useEffect } from 'react';
 import axios from 'axios';

 function App() {
   const [products, setProducts] = useState([]);

   useEffect(() => {
     fetchProducts();
   }, []);

   const fetchProducts = async () => {
     try {
       const response = await axios.get('http://localhost:8080/api/products');
       setProducts(response.data);
     } catch (error) {
       console.error('Error fetching products:', error);
     }
   };

   return (
     <div className="min-h-screen bg-gray-100 p-8">
       <h1 className="text-3xl font-bold mb-6 text-center">E-Commerce Products</h1>
       <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
         {products.map(product => (
           <div key={product.id} className="bg-white p-4 rounded shadow">
             <h2 className="text-xl font-semibold">{product.name}</h2>
             <p className="text-gray-600">{product.description}</p>
             <p className="text-green-600 font-bold">${product.price}</p>
           </div>
         ))}
       </div>
     </div>
   );
 }

 export default App;
