
outputfile = File.open("credit_card_clients.arff", 'w');
outputfileTest = File.open("credit_card_clients_test.arff", 'w');
linecount = 0;
File.readlines("default of credit card clients.csv").each do |line|
   unless linecount == 0
      tokens = line.strip.split(',')
      tokens = tokens.drop(1)
      if linecount == 1
         outputfile.write("@relation credit-card-clients\n")
         outputfileTest.write("@relation credit-card-clients\n")
         tokens.each do |attr|
            outputfile.write("@attribute #{attr.strip.gsub(/\s/, '_')} numeric \n")
            outputfileTest.write("@attribute #{attr.strip.gsub(/\s/, '_')} numeric \n")
         end
         outputfile.write("\n@data\n")
         outputfileTest.write("\n@data\n")
      else
         if linecount < 350
            outputfile.write("#{tokens.join(",")}\n")
         elsif linecount < 370
            tokens[0] = '?'
            outputfileTest.write("#{tokens.join(",")}\n")
         else
            break
         end
      end
   end
   linecount = linecount + 1
end

outputfile.close
outputfileTest.close

puts "Credit card .arff fiels created successfully"
exit 0
