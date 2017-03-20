(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonPopularity', PersonPopularity);

    PersonPopularity.$inject = ['$resource'];

    function PersonPopularity ($resource) {
        var resourceUrl =  'api/person-popularities/:id';

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
                url: 'api/person-popularities/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();