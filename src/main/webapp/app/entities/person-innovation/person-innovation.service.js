(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonInnovation', PersonInnovation);

    PersonInnovation.$inject = ['$resource'];

    function PersonInnovation ($resource) {
        var resourceUrl =  'api/person-innovations/:id';

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
                url: 'api/person-innovations/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();