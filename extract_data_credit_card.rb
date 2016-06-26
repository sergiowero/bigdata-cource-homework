
outputfile = File.open("credit_card_clients.arff", 'w');
linecount = 0;
File.readlines("default of credit card clients.csv").each do |line|
   unless linecount == 0
      tokens = line.strip.split(',')
      tokens = tokens.drop(1)
      if linecount == 1
         outputfile.write("@relation credit-card-clients\n")
         tokens.each do |attr|
            outputfile.write("@attribute #{attr.strip.gsub(/\s/, '_')} numeric \n")
         end
         outputfile.write("\n@data\n")
      else
         outputfile.write("#{tokens.join(",")}\n")
      end
   end
   linecount = linecount + 1
   break if linecount > 350
end

outputfile.close
