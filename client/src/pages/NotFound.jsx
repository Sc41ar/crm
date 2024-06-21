import { Link } from 'react-router-dom';

export default function Component() {
    return (
      <div className="flex flex-col items-center justify-center min-h-[100dvh] px-4 md:px-6">
<div className="max-w-md px-4 py-12 text-center">
        <h1 className="text-9xl font-bold">404</h1>
        <p className="mt-4 text-muted-foreground">Oops! The page you're looking for could not be found.</p>

          <Link
            to="/"
            className="inline-flex h-10 items-center justify-center rounded-md bg-primary px-6 text-sm font-medium text-primary-foreground shadow transition-colors hover:bg-primary/90 focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:pointer-events-none disabled:opacity-50"
          >
            Go back home
          </Link>
        </div>
      </div>
    )
  }