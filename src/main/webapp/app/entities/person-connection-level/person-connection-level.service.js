(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonConnectionLevel', PersonConnectionLevel);

    PersonConnectionLevel.$inject = ['$resource'];

    function PersonConnectionLevel ($resource) {
        var resourceUrl =  'api/person-connection-levels/:id';

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
                url: 'api/person-connection-levels/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
