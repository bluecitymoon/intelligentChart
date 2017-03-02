(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonWordCloud', PersonWordCloud);

    PersonWordCloud.$inject = ['$resource'];

    function PersonWordCloud ($resource) {
        var resourceUrl =  'api/person-word-clouds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
