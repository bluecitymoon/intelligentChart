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
            'update': { method:'PUT' },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-word-clouds/person/:id',
                params: { id : '@id', size: 1000}
            },
            'loadCalculatedByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-word-clouds/person/calculated/:id',
                params: { id : '@id', size: 1000}
            }
        });
    }
})();
