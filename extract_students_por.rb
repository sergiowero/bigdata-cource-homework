class String
    def is_i?
       /\A[-+]?\d+\z/ === self
    end
end

outputfile = File.open("students_por.arff", 'w');
outputfileTest = File.open("students_por_test.arff", 'w');
linecount = 0;

attributes = []
map = []
File.readlines("student-por.csv").each do |line|
   tokens = line.strip.split(';')
   if linecount == 0
      attributes = tokens.dup
      attributes.each { |a| map << [] }
   else
      if linecount <= 370
         tokens.each_index { |i| map[i] << tokens[i].strip.gsub(/\"/, "") }
      else
         break
      end
   end
   linecount = linecount + 1
end

outputfile.write("@relation students\n")
outputfileTest.write("@relation students\n")

# Write attibutes
attributes.each_index do |i|
   attr = attributes[i]
   type = "numeric"
   unless map[i][0].is_i?
      elems = map[i].uniq.map { |e| "'#{e}'" }
      type = "{#{elems.join(', ')}}"
   end
   outputfile.write("@attribute #{attr.strip.gsub(/\s/, '_')} #{type}\n")
   outputfileTest.write("@attribute #{attr.strip.gsub(/\s/, '_')} #{type}\n")
end

outputfile.write("\n@data\n")
outputfileTest.write("\n@data\n")

length = map[0].length

# Write data
length.times do |i|
   data = map.map { |e| e[i] }
   if i <= 350
      outputfile.write("#{data.join(',')}\n")
   else
      data[17] = '?'
      outputfileTest.write("#{data.join(',')}\n")
   end
end

outputfile.close
outputfileTest.close

puts "Students .arff files created successfully"
exit 0
