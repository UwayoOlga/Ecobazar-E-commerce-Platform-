import ProductList from '../components/ProductList';
import { Link } from 'react-router-dom';

const Home = () => (
  <div className="p-8">
    <h1 className="text-3xl font-bold mb-6 text-center">Product List</h1>
    <Link to="/add" className="block text-center bg-green-600 text-white py-2 rounded mb-4">Add New Product</Link>
    <ProductList />
  </div>
);

export default Home;
