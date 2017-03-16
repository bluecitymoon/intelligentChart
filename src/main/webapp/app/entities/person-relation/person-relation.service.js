(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonRelation', PersonRelation);

    PersonRelation.$inject = ['$resource'];

    function PersonRelation ($resource) {
        var resourceUrl =  'api/person-relations/:id';

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
                url: 'api/person-relations/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
