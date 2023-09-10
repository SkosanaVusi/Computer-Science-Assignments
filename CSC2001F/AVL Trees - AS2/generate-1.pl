# Generate random UCT-like students IDs
# Hussein Suleman
# 25 March 2021

srand ();

# get first names and last names listings
open ( my $f, "firstnames.txt");
my @dataf = <$f>;
close ($f);
open ( my $f, "lastnames.txt");
my @datal = <$f>;
close ($f);
foreach my $name (@dataf) { chomp $name; }
foreach my $name (@datal) { chomp $name; }

my $counter = {};

my @allstudents = map {
   my $first = $dataf[int(rand ($#dataf))];
   my $last = $datal[int(rand ($#datal))];
   my $firstID = substr (uc ($first), 0, 3);
   my $lastID = uc($last);
   $lastID =~ s/[AEIOU]//g;
   $lastID = substr ($lastID.'XXX', 0, 3);
   
   my $person = 1;
   if (defined $counter->{$lastID.$firstID})
   { $person = $counter->{$lastID.$firstID} + 1; }
   $counter->{$lastID.$firstID} = $person;

   sprintf ("%s%s%03d %s %s\n", $lastID, $firstID, $person, $first, $last);
} 1..100000;

my @randomstudents = ();
for ( my $i=0; $i<5000; $i++ )
{
   my $pick = int (rand(100000));
   while ($allstudents[$pick] eq '')
   { $pick = int (rand(100000)); }
   $randomstudents[$i] = $allstudents[$pick];
   $allstudents[$pick] = ''; 
}

foreach my $s (@randomstudents)
{
   print $s;
}

