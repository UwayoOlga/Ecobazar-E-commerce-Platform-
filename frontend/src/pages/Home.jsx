import ProductList from '../components/ProductList';
import { Link } from 'react-router-dom';
import bgImage from '../assets/react.svg';

const Home = () => (
  <div className="min-h-screen w-full bg-gradient-to-br from-green-50 to-green-200 flex flex-col">
    <div
      className="relative flex flex-col items-center justify-center text-center py-20 px-4"
      style={{
        backgroundImage: `url(${bgImage})`,
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'right bottom',
        backgroundSize: '300px',
      }}
    >
      <h1 className="text-4xl md:text-5xl font-extrabold mb-4 text-green-900 drop-shadow-lg">Welcome to E-Commerce!</h1>
      <p className="text-lg md:text-xl text-green-800 mb-8 max-w-2xl mx-auto">
        Discover the best products, enjoy seamless shopping, and experience a fresh, modern interface.
      </p>
      <Link
        to="/register"
        className="inline-block bg-green-700 hover:bg-green-800 text-white font-semibold px-8 py-3 rounded-full shadow-lg transition mb-4"
      >
        Get Started
      </Link>
    </div>
    <div className="max-w-6xl mx-auto w-full px-4 py-10">
      <h2 className="text-2xl font-bold mb-6 text-green-900 text-center">Products</h2>
      <ProductList />
    </div>
  </div>
);

export default Home;
