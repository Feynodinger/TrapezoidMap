# TrapezoidMap
Initialize x-coordinates-list-all-polygons.
for each polygon pg
	for each point pt of pg
	Add pt to x-coordinates-list-all-polygons

Sort x-coordinates-list-all-polygons to get sorted-x-coordinates-list-all-polygons

Initialize List of Vertical Slabs.
Every Vertical Slab has list of Trapezoid Faces.


//Construct initial trapezoids.
For all points of all polygons
	Create trapezoid (which is basically rectangle) with top trapezoid line and bottom trapezoid line.
	Mark trapezoid with array index of the points. 
	
Add the last trapezoid in the list.

Initialize List of all Polylines ( For all polygons). Call it allPolyLines.
// traverse through polygons and create a list of all lines.
For all polygons
	For all points of the polygon
		Add a polyline with "Point at the current index of polygon" and "Point at the next index of polygon".
		(Between these two indices, the point whose x-cordinate comes before the x-cordinate of other point, becomes the start point.
		So this is not at all a sorted list of lines of the polygon w.r.t. x-cordinate of start point.
		I don't think this comparison logic adds any value to the algorithm.)

// Now will start splitting the initial trapezoids(vertical slabs)

// Note: No splitting needed on first and last trapezoid.

For each vertical slab VS (i.e. initial rectangle/trapezoid)	//index i

	Initialize a blank list of trapezoid lines, say TLlist.
	Get the first trapezoid of VS. In this case only one trapezoid is there and that is rectangle.
	Initialize start and end x values of top line of this rectangle.
	
	For each polyline PLine of allPolyLines
	// If Current line divides current trapezoid
	
		------------Determine leftp-----------
		If current line starts on trapezoid boundary
		Start point of PLine is the leftp.
		Else 
		Find y-intersection for PLine and start x value of top line of this rectangle.
		leftp = (start x value of top line of this rectangle, above calculated y-intersection)
		
		------------Determine rightp----------
		If current line ends on trapezoid boundary
		End point of PLine is the rightp.
		Else 
		Find y-intersection for PLine and end x value of top line of this rectangle.
		rightp = (end x value of top line of this rectangle, above calculated y-intersection)
	
	Add new trapezoid line to TLlist	
	//Upper If End
	
	//End of Inner For Loop
	
	Sort all trapezoid lines TLlist based on y-co-odinate height.
	Remove initial trapezoid list for the vertical slab, i.e. the lone rectangle.
	Construct all new trapezoids for the current vertical slab from the above sorted trapezoid lines TLlist.
	//As the TLlist is already sorted, we can take consequitive trapezoid lines in pairs and carry on creating trapezoids. Lets say index used is j.

	Mark the index of these trapezoids as i+1, j+1.
	Also add the topmost trapezoid into the new trapezoid list.
	
	Add all of these new trapezoids to the empty trapezoid list for the current vertical slab.
	
//End of Outer Loop handling vertical slabs.
